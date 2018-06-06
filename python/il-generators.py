import numpy
import xml.etree.ElementTree

class ILGenerator:
    id = None

    def __init__(self, id):
        self.id = id

    def _evaluate_result(self, result):
        for x in range(0, result.size):
            if isinstance(result[x], ILGenerator):
                result[x] = result[x].generate(1)[0];

    def generate(self, count):
        return None

class ILRandomGenerator(ILGenerator):
    v = None
    p = None

    def __init__(self, id, values=None, probabilities=None):
        ILGenerator.__init__(self, id)
        self.v = values
        self.p = probabilities

    def generate(self, count):
        result = numpy.random.choice(self.v, p=self.p, size=count)
        self._evaluate_result(result)
        return result

class ILXmlParser:
    data = {}

    def fromFile(self, path):
        tree = xml.etree.ElementTree.parse(path)
        self._parse(tree.getroot())

    def fromString(self, stringData):
        root = xml.etree.ElementTree.fromstring(stringData)
        self._parse(root)

    def  _parse(self, root):
        if root.tag == "data":
            for child in root:
                if child.tag == "random":
                    generator = self._parseRandom(child)
                    self.data[generator.id] = generator

    def _parseRandom(self, element):
        id = element.get("id")
        values = []
        probabilities = []
        for child in element:
            if child.tag == "item":
                i = self._parseItem(child)
                values.append(i['value'])
                if 'probability' in i:
                    probabilities.append(float(i['probability']))
                else:
                    probabilities.append(1)
        #Probabilities are normalized
        probabilities = [float(i)/sum(probabilities) for i in probabilities]
        return ILRandomGenerator(id, values, probabilities)

    def _parseItem(self, element):
        d = {}
        d.update(element.attrib)
        if self._hasChildren(element):
            #Use only 1st element
            child = element[0]
            if child.tag == "random":
                v = self._parseRandom(child)
            elif child.tag == 'reference':
                #TODO check if referenced generator exists
                v = self.data[child.get("id")]
        else:
            v = element.text
        d['value'] = v
        return d

    def _hasChildren(self, element):
        return bool(len(element))

p = ILXmlParser()
p.fromFile("generators.xml")
p.fromString("<?xml version=\"1.0\"?><data><random id=\"common.color2\"><item>black</item><item>white</item><item>red</item><item>green</item><item>blue</item></random></data>")
g = ILRandomGenerator("test", numpy.array(["a","b"]), numpy.array([0.5,0.5]))
print(g.generate(10))
q = p.data["Alphabet"]
print(q.generate(10))
q = p.data["common.color2"]
print(q.generate(10))

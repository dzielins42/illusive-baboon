package pl.dzielins42.illusivebaboon.android.view.list;

import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface GeneratorListView extends MvpView {
    /**
     * Render state.
     *
     * @param state state to be rendered by the view.
     */
    void render(GeneratorListState state);
}

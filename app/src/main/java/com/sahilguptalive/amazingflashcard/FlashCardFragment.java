package com.sahilguptalive.amazingflashcard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sahilguptalive.amazingflashcard.dbmanager.BookmarkedWordDbManager;
import com.sahilguptalive.storage.models.BookmarkedWord;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 21-05-2017.
 */
public class FlashCardFragment extends BaseFragment {


    private static final String KEY_BOOKMARKED_WORD_ID = "key_bookmarked_word_id";
    @BindView(R.id.flash_card_fragment_text_view_word)
    TextView mFlashCardFragmentTextViewWord;
    @BindView(R.id.flash_card_fragment_action_text_view_reveal)
    TextView mFlashCardFragmentActionTextViewReveal;
    Unbinder unbinder;
    @BindView(R.id.flash_card_fragment_front_container_layout)
    RelativeLayout mFlashCardFragmentFrontContainerLayout;
    @BindView(R.id.flash_card_fragment_flipped_container_layout)
    RelativeLayout mFlashCardFragmentFlippedContainerLayout;

    static Fragment createInstance(final BookmarkedWord bookmarkedWord) {
        final FlashCardFragment fragment = new FlashCardFragment();
        fragment.setArguments(createArg(bookmarkedWord));
        return fragment;
    }

    private static Bundle createArg(final BookmarkedWord bookmarkedWord) {
        final Bundle bundle = new Bundle();
        bundle.putInt(KEY_BOOKMARKED_WORD_ID, bookmarkedWord.getId());
        return bundle;
    }

    private int getWordId() {
        if (getArguments() == null
                || !getArguments().containsKey(KEY_BOOKMARKED_WORD_ID)) {
            throw new IllegalArgumentException("bookmark is not passed");
        }
        return getArguments().getInt(KEY_BOOKMARKED_WORD_ID);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flash_card, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupUnRevealedView();
        return view;
    }

    private void setupUnRevealedView() {
        BookmarkedWord word = BookmarkedWordDbManager.getWordForId(getWordId());
        if (word == null) {
            throw new IllegalArgumentException("word for word id does not exist.");
        }
        mFlashCardFragmentTextViewWord.setText(word.getWord());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.flash_card_fragment_action_text_view_reveal)
    public void onRevealClicked() {
        mFlashCardFragmentFlippedContainerLayout.setVisibility(View.VISIBLE);
        mFlashCardFragmentFrontContainerLayout.setVisibility(View.GONE);
    }
}

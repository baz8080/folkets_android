package com.mbcdev.folkets

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Tests for [WordsRecyclerAdapter]
 *
 * Created by barry on 04/08/2017.
 */
class WordsRecyclerAdapterTests {

    @Mock private lateinit var wordList: List<Word>
    @Mock private lateinit var presenter: MainMvp.Presenter

    private lateinit var adapter: WordsRecyclerAdapter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        adapter = WordsRecyclerAdapter(wordList, presenter)
    }

    @Test
    fun `constructor does not invoke any list methods`() {
        WordsRecyclerAdapter(wordList, presenter)
        verifyZeroInteractions(wordList)
    }

    @Test
    fun `constructor does not invoke any presenter methods`() {
        WordsRecyclerAdapter(wordList, presenter)
        verifyZeroInteractions(presenter)
    }

    @Test
    fun `adapter size is the size of the word list`() {
        `when`(wordList.size).thenReturn(42)
        assertThat(adapter.itemCount).isEqualTo(42)
    }

    @Test
    fun `onBindViewHolder can handle null view holders`() {
        val word = mock(Word::class.java)
        `when`(wordList[0]).thenReturn(word)
        adapter.onBindViewHolder(null, 0)
    }

    @Test
    fun `onBindViewHolder can handle null words`() {
        val viewHolder = mock(WordsRecyclerAdapter.ViewHolder::class.java)
        `when`(wordList[0]).thenReturn(null)
        adapter.onBindViewHolder(viewHolder, 0)
    }

    @Test
    fun `viewHolder has the expected views`() {
        val view = getMockedView()
        val viewHolder = WordsRecyclerAdapter.ViewHolder(view)

        verify(view).findViewById<TextView>(eq(R.id.recycler_view_word))
        verify(view).findViewById<TextView>(eq(R.id.recycler_view_type))
        verify(view).findViewById<TextView>(eq(R.id.recycler_view_translation))
        verify(view).findViewById<ImageView>(eq(R.id.recycler_view_tts))
        verifyNoMoreInteractions(view)

        assertThat(viewHolder.wordTextView).isNotNull()
        assertThat(viewHolder.wordTypeTextView).isNotNull()
        assertThat(viewHolder.translationTextView).isNotNull()
        assertThat(viewHolder.ttsImageView).isNotNull()
        assertThat(viewHolder.holderView).isNotNull()
        assertThat(viewHolder.holderView.context).isNotNull()
    }

    @Test
    fun `wordTextView gets called with the correct text`() {
        val view = getMockedView()
        val viewHolder = WordsRecyclerAdapter.ViewHolder(view)

        val word = getMockedWord()
        `when`(wordList[0]).thenReturn(word)

        adapter.onBindViewHolder(viewHolder, 0)
        verify(viewHolder.wordTextView).text = "Glass"
    }

    @Test
    fun `wordTextView has correct flag drawable`() {
        val view = getMockedView()
        val viewHolder = WordsRecyclerAdapter.ViewHolder(view)

        val word = getMockedWord()
        `when`(wordList[0]).thenReturn(word)

        adapter.onBindViewHolder(viewHolder, 0)
        verify(viewHolder.wordTextView)
                .setCompoundDrawablesWithIntrinsicBounds(R.drawable.flag_sv, 0, 0, 0)
    }

    @Test
    fun `wordTypeTextView gets called with the correct text`() {
        val view = getMockedView()
        val viewHolder = WordsRecyclerAdapter.ViewHolder(view)

        val word = getMockedWord()
        `when`(wordList[0]).thenReturn(word)

        adapter.onBindViewHolder(viewHolder, 0)
        verify(viewHolder.wordTypeTextView).text = "Mocked noun"
    }

    @Test
    fun `translationTextView gets is gone from view when there are no translations`() {
        val view = getMockedView()
        val viewHolder = WordsRecyclerAdapter.ViewHolder(view)

        val word = getMockedWord()

        val wordsWithComments = mock(WordsWithComments::class.java)
        `when`(wordsWithComments.wordsFormattedForDisplay).thenReturn("")
        `when`(word.translations).thenReturn(wordsWithComments)
        `when`(wordList[0]).thenReturn(word)

        adapter.onBindViewHolder(viewHolder, 0)
        verify(viewHolder.translationTextView).visibility = View.GONE
    }

    @Test
    fun `translationTextView gets is shown when there are translations`() {
        val view = getMockedView()
        val viewHolder = WordsRecyclerAdapter.ViewHolder(view)

        val word = getMockedWord()

        val wordsWithComments = mock(WordsWithComments::class.java)
        `when`(wordsWithComments.wordsFormattedForDisplay).thenReturn("Plumbus")
        `when`(word.translations).thenReturn(wordsWithComments)
        `when`(wordList[0]).thenReturn(word)

        adapter.onBindViewHolder(viewHolder, 0)
        verify(viewHolder.translationTextView).visibility = View.VISIBLE
        verify(viewHolder.translationTextView).text = "Plumbus"
    }

    @Test
    fun `holder root view onClickListener calls presenter to select a word`() {
        val view = getMockedView()
        val viewHolder = WordsRecyclerAdapter.ViewHolder(view)

        val word = getMockedWord()
        `when`(wordList[0]).thenReturn(word)

        adapter.onBindViewHolder(viewHolder, 0)
        val clickCaptor = ArgumentCaptor.forClass(View.OnClickListener::class.java)
        verify(viewHolder.holderView).setOnClickListener(clickCaptor.capture())

        val clickListener = clickCaptor.value
        assertThat(clickListener).isNotNull()
        clickListener.onClick(view)
        verify(presenter).onWordSelected(word)
    }

    @Test
    fun `tts view onClickListener calls presenter to speak a word`() {
        val view = getMockedView()
        val viewHolder = WordsRecyclerAdapter.ViewHolder(view)

        val word = getMockedWord()
        `when`(wordList[0]).thenReturn(word)

        adapter.onBindViewHolder(viewHolder, 0)
        val clickCaptor = ArgumentCaptor.forClass(View.OnClickListener::class.java)
        verify(viewHolder.ttsImageView).setOnClickListener(clickCaptor.capture())

        val clickListener = clickCaptor.value
        assertThat(clickListener).isNotNull()
        clickListener.onClick(view)
        verify(presenter).onTtsRequested(word)
    }

    private fun getMockedWord(): Word {
        val word = mock(Word::class.java)

        `when`(word.word).thenReturn("Glass")
        `when`(word.flag).thenReturn(R.drawable.flag_sv)
        `when`(word.wordTypes).thenReturn(listOf(WordType.NOUN))

        val wordWithComments = mock(WordsWithComments::class.java)
        `when`(word.translations).thenReturn(wordWithComments)

        return word
    }

    private fun getMockedView(): View {
        val view = mock(View::class.java)
        val context = mock(Context::class.java)

        val wordTextView = mock(TextView::class.java)
        val wordTypeTextView = mock(TextView::class.java)
        val translationTextView = mock(TextView::class.java)
        val ttsImageView = mock(ImageView::class.java)

        `when`(context.getString(R.string.word_type_noun)).thenReturn("Mocked noun")
        `when`(view.context).thenReturn(context)
        `when`(view.findViewById<TextView>(R.id.recycler_view_word)).thenReturn(wordTextView)
        `when`(view.findViewById<TextView>(R.id.recycler_view_type)).thenReturn(wordTypeTextView)
        `when`(view.findViewById<TextView>(R.id.recycler_view_translation))
                .thenReturn(translationTextView)
        `when`(view.findViewById<ImageView>(R.id.recycler_view_tts)).thenReturn(ttsImageView)

        return view
    }
}
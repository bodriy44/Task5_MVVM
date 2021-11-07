package com.example.task5_mvvm.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.task5_mvvm.model.IMainModel
import com.example.task5_mvvm.model.MainModel
import com.example.task5_mvvm.model.Note
import com.example.task5_mvvm.viewmodel.MainViewModel
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class MainViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: IMainModel

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = Mockito.mock(MainModel::class.java)
        viewModel = MainViewModel(repository)

        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun cleanUp() {
        Mockito.reset(repository)
    }

    @Test
    fun testAddEmptyNote() {
        viewModel.addNote(Note("", ""))
        var error = false
        viewModel.onSuccessSaveNote.observeForever {
            error = true
        }
        assertFalse(error)

        var success = false
        viewModel.onErrorSaveNote.observeForever {
            success = true
        }
        assertTrue(success)
    }

    @Test
    fun testAddNullNote() {
        viewModel.addNote(Note("null", "null"))

        var error = false
        viewModel.onSuccessSaveNote.observeForever {
            error = true
        }
        assertFalse(error)

        var success = false
        viewModel.onErrorSaveNote.observeForever {
            success = true
        }
        assertTrue(success)
    }

    @Test
    fun testAddNDataNote() {
        viewModel.addNote(Note("Header", "Body"))

        var error = false
        viewModel.onErrorSaveNote.observeForever {
            error = true
        }
        assertFalse(error)

        var success = false
        viewModel.onSuccessSaveNote.observeForever {
            success = true
        }
        assertTrue(success)
    }

    @Test
    fun testChangeDataNote() {
        val note = Note("Header", "Body")
        viewModel.addNote(note)
        viewModel.setCurrentNoteIndex(0)
        viewModel.changeCurrentNote()

        var error = false
        viewModel.onErrorChangeNote.observeForever {
            error = true
        }
        assertFalse(error)

        var success = false
        viewModel.onSuccessChangeNote.observeForever {
            success = true
        }
        assertTrue(success)
    }

    @Test
    fun testChangeNonExistNote() {
        val note = Note("Header", "Body")
        viewModel.addNote(note)
        viewModel.setCurrentNoteIndex(1)
        viewModel.changeCurrentNote()

        var error = false
        viewModel.onErrorChangeNote.observeForever {
            error = true
        }
        assertFalse(error)

        var success = false
        viewModel.onSuccessChangeNote.observeForever {
            success = true
        }
        assertTrue(success)
    }
}
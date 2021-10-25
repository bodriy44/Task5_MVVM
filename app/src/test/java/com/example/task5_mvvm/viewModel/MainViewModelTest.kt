package com.example.task5_mvvm.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.lifecycleScope
import com.example.task5_mvvm.model.IMainModel
import com.example.task5_mvvm.model.MainModel
import com.example.task5_mvvm.model.Note
import com.example.task5_mvvm.model.database.AppDatabase
import com.example.task5_mvvm.view.MainActivity
import com.example.task5_mvvm.viewmodel.MainViewModel
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.setMain
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
    private var note: Note? = null

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = Mockito.mock(MainModel::class.java)
        viewModel = MainViewModel(repository)

        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun testAddEmptyNote() {
        viewModel.addNote("", "")
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
        viewModel.addNote("null", "null")

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
        viewModel.addNote("Header", "Body")

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
        viewModel.changeNote(Note("Header", "Body"))

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
    fun testChangeNullNote() {
        viewModel.changeNote(Note("null", "null"))

        var error = false
        viewModel.onSuccessChangeNote.observeForever {
            error = true
        }
        assertFalse(error)

        var success = false
        viewModel.onErrorChangeNote.observeForever {
            success = true
        }
        assertTrue(success)
    }

    @Test
    fun testChangeEmptyNote() {
        viewModel.changeNote(Note("", ""))
        var error = false
        viewModel.onSuccessChangeNote.observeForever {
            error = true
        }
        assertFalse(error)

        var success = false
        viewModel.onErrorChangeNote.observeForever {
            success = true
        }
        assertTrue(success)
    }
}
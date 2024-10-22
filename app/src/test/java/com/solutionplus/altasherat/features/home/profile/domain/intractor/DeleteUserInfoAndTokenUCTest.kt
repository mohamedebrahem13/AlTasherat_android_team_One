package com.solutionplus.altasherat.features.home.profile.domain.intractor

import com.solutionplus.altasherat.common.data.models.Resource
import com.solutionplus.altasherat.common.data.models.exception.AlTasheratException
import com.solutionplus.altasherat.features.home.profile.domain.repository.local.IProfileLocalDataSource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DeleteUserInfoAndTokenUCTest{
    private lateinit var repository: FakeProfileRepository
    private lateinit var profileRemoteDataSource: FakeProfileRemoteDataSource
    private lateinit var profileLocalDataSource: IProfileLocalDataSource
    private lateinit var useCase: DeleteUserInfoAndTokenUC
    @Before
    fun setUp() {
        profileRemoteDataSource = FakeProfileRemoteDataSource()
        profileLocalDataSource = FakeProfileLocalDataSource()
        repository = FakeProfileRepository(profileRemoteDataSource,profileLocalDataSource)
        useCase = DeleteUserInfoAndTokenUC(repository)
    }
    @Test
    fun `execute use case should emit success state`() = runTest {
        // When
        val resultFlow = useCase(Unit)
        val resultList = resultFlow.take(2).toList()

        // Then
        // Expecting two states: Progress and Success
        assertEquals(2, resultList.size)

        // Verify the success state
        val successState = resultList.last()
        assertTrue(successState is Resource.Success)
    }
    @Test
    fun `execute use case should emit loading state`() = runTest {
        // When
        val resultFlow = useCase(Unit)
        val resultList = resultFlow.take(1).toList()

        // Then
        // Expecting one state: Progress
        assertEquals(1, resultList.size)

        // Verify the loading state
        val loadingState = resultList.first()
        assertTrue(loadingState is Resource.Progress)
        assertTrue((loadingState as Resource.Progress).loading)
    }
    @Test
    fun `execute use case should emit failure state when an error occurs`() = runTest {
        // Given that the delete operations fail
        repository.shouldThrowException = true

        // When
        val resultFlow = useCase(Unit)
            .catch { throwable ->
                // Ensure that the caught exception is the expected one
                assertTrue(throwable is AlTasheratException.Unknown)
            }
        val resultList = resultFlow.take(2).toList()

        // Then
        // Expecting two states: Progress and Failure
        assertEquals(2, resultList.size)

        // Verify the failure state
        val failureState = resultList.last()
        assertTrue(failureState is Resource.Failure)
    }
}
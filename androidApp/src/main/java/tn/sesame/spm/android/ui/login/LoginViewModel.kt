package tn.sesame.spm.android.ui.login

import androidx.lifecycle.ViewModel
import tn.sesame.spm.domain.usecases.SesameUsersUsecase

class LoginViewModel(
    private val sesameUsersUsecase: SesameUsersUsecase
) : ViewModel() {
}
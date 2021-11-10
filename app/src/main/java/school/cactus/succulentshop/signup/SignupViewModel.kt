package school.cactus.succulentshop.signup

import school.cactus.succulentshop.infra.BaseViewModel

class SignupViewModel : BaseViewModel() {
    fun onButtonAlreadyHaveAccountClick() {
        navigateToLoginScreen()
    }

    private fun navigateToLoginScreen() {
        val directions = SignupFragmentDirections.actionSignupFragmentToLoginFragment()
        navigation.navigate(directions)
    }
}
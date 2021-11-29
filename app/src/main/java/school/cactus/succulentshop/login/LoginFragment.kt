package school.cactus.succulentshop.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import school.cactus.succulentshop.R
import school.cactus.succulentshop.auth.AuthRepository
import school.cactus.succulentshop.auth.JwtStore
import school.cactus.succulentshop.common.hideKeyboard
import school.cactus.succulentshop.databinding.FragmentLoginBinding
import school.cactus.succulentshop.infra.BaseFragment

class LoginFragment : BaseFragment() {
    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    override val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(
            store = JwtStore(requireContext()),
            repository = AuthRepository()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = getString(R.string.log_in)

        viewModel.showKeyboardState.observe(viewLifecycleOwner) {
            if (!it) {
                hideKeyboard()
                viewModel.showKeyboardState.value = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
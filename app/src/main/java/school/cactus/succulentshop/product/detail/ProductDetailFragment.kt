package school.cactus.succulentshop.product.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import school.cactus.succulentshop.R
import school.cactus.succulentshop.databinding.FragmentProductDetailBinding
import school.cactus.succulentshop.infra.BaseFragment
import school.cactus.succulentshop.product.ProductRepository

class ProductDetailFragment : BaseFragment() {
    private var _binding: FragmentProductDetailBinding? = null

    private val binding get() = _binding!!

    private val args: ProductDetailFragmentArgs by navArgs()

    override val viewModel: ProductDetailViewModel by viewModels {
        ProductDetailViewModelFactory(args.productId, ProductRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = getString(R.string.app_name)

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val adapter = RelatedProductAdapter()
        viewModel.relatedProducts.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(RelatedProductDecoration())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
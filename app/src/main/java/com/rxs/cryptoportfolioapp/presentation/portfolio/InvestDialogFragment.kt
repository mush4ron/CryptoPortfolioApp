package com.rxs.cryptoportfolioapp.presentation.portfolio


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.rxs.cryptoportfolioapp.databinding.FragmentInvestDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InvestDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentInvestDialogBinding

    @Inject
    lateinit var viewModel: PortfolioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInvestDialogBinding.inflate(layoutInflater)
        setupStyle()
        setupButtons()

        return binding.root
    }

    private fun setupButtons() {
        binding.apply {
            btnDialogInvestInvest.setOnClickListener {
                val value = binding.etDialogInvestValue.text.toString()
                if (value.isNotBlank()) {
                    viewModel.addBalance(value = value.toInt())
                }
                dismiss()
            }

            btnDialogInvestBack.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun setupStyle() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val marginInDp = 24
        val marginInPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            marginInDp.toFloat(),
            requireContext().resources.displayMetrics
        ).toInt()

        val layoutParams = dialog?.window?.attributes
        layoutParams?.gravity = Gravity.TOP
        layoutParams?.y = marginInPixels
        dialog?.window?.attributes = layoutParams
    }
}
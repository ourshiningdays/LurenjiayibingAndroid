package com.qxy.example.ui.rank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.qxy.example.databinding.FragmentRankBinding

class RankFragment : Fragment() {

    private var _binding: FragmentRankBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val rankViewModel =
                ViewModelProvider(this).get(RankViewModel::class.java)

        _binding = FragmentRankBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textRank
        rankViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
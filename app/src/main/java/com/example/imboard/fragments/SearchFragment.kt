package com.example.imboard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.imboard.R
import com.example.imboard.databinding.FragmentSearchBinding
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

class SearchFragment : Fragment() {
    private var binding : FragmentSearchBinding by autoCleared()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
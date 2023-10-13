package com.neatroots.instagram.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neatroots.instagram.R
import com.neatroots.instagram.databinding.FragmentMyPostBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPostFragment : Fragment() {
    private lateinit var binding: FragmentMyPostBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentMyPostBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {

    }
}
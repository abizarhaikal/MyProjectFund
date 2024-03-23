package com.example.myprojectfund.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprojectfund.databinding.FragmentFollBinding
import com.example.myprojectfund.ui.adapter.DetailAdapter
import com.example.myprojectfund.ui.viewmodel.FragmentModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [FollFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollFragment : Fragment() {

    private lateinit var binding: FragmentFollBinding


    override fun onCreateView(
        inflater: LayoutInflater ,container: ViewGroup? ,savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollBinding.inflate(inflater ,container ,false)
        return binding.root
    }

    private var position: Int? = null
    private lateinit var username: String
    private lateinit var adapterDetail: DetailAdapter

    override fun onViewCreated(view: View ,savedInstanceState: Bundle?) {
        super.onViewCreated(view ,savedInstanceState)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFolls.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity ,layoutManager.orientation)
        binding.rvFolls.addItemDecoration(itemDecoration)
        val fragmentViewModel = ViewModelProvider(
            this ,ViewModelProvider.NewInstanceFactory()
        ).get(FragmentModel::class.java)
        adapterDetail = DetailAdapter()
        arguments?.let {
            position = it.getInt(ARG_POSITION ,0)
            username = it.getString(ARG_USERNAME).toString()
        }
        if (position == 1) {
            fragmentViewModel.getFollowers(user = username)
            fragmentViewModel.listItemDetail.observe(viewLifecycleOwner ,Observer {
                adapterDetail.submitList(it)
                binding.rvFolls.adapter = adapterDetail
            })
            fragmentViewModel.isLoading.observe(viewLifecycleOwner) {
                isLoading(it)
            }

        } else {
            fragmentViewModel.getFollowings(username)
            fragmentViewModel.listItemDetail.observe(viewLifecycleOwner ,Observer {
                adapterDetail.submitList(it)
                binding.rvFolls.adapter = adapterDetail
            })
            fragmentViewModel.isLoading.observe(viewLifecycleOwner) {
                isLoading(it)
            }

        }
    }

    private fun isLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }


}
import Vue from 'vue'
import Router from 'vue-router'
//import HelloWorld from '@/components/HelloWorld'
import MemCharts from '@/components/charts/MemCharts'
import CpuCharts from '@/components/charts/CpuCharts'
import GeneralWithPositionCharts from '@/components/charts/GeneralWithPositionCharts'
import GeneralNoPositionCharts from '@/components/charts/GeneralNoPositionCharts'
import IdCardFrontCharts from '@/components/charts/IdCardFrontCharts'
import IdCardBackCharts from '@/components/charts/IdCardBackCharts'

import MemList from '@/components/list/MemList'
import CpuList from '@/components/list/CpuList'
import GeneralNoPositionList from '@/components/list/GeneralNoPositionList'
import GeneralWithPositionList from '@/components/list/GeneralWithPositionList'
import IdCardBackList from '@/components/list/IdCardBackList'
import IdCardFrontList from '@/components/list/IdCardFrontList'
import FunctionTestResultList from '@/components/list/FunctionTestResultList'
import FunctionTestList from '@/components/list/FunctionTestList'
import PerformanceResultList from '@/components/list/PerformanceResultList'
import ProjectList from '@/components/list/ProjectList'


import index from '@/components/Index'

Vue.use(Router)

export default new Router({
	base: '/sdk/',
	routes: [{
			path: '/',
			name: 'index',
			component: index
		},
		{
			path: '/MemCharts',
			name: 'MemCharts',
			component: MemCharts
		},
		{
			path: '/ProjectList',
			name: 'ProjectList',
			component: ProjectList
		},
		{
			path: '/CpuCharts',
			name: 'CpuCharts',
			component: CpuCharts
		},
		{
			path: '/GeneralWithPositionCharts',
			name: 'GeneralWithPositionCharts',
			component: GeneralWithPositionCharts
		},
		{
			path: '/GeneralNoPositionCharts',
			name: 'GeneralNoPositionCharts',
			component: GeneralNoPositionCharts
		},
		{
			path: '/IdCardFrontCharts',
			name: 'IdCardFrontCharts',
			component: IdCardFrontCharts
		},
		{
			path: '/IdCardBackCharts',
			name: 'IdCardBackCharts',
			component: IdCardBackCharts
		},
		{
			path: '/CpuList',
			name: 'CpuList',
			component: CpuList
		},
		{
			path: '/MemList',
			name: 'MemList',
			component: MemList
		},
		{
			path: '/GeneralNoPositionList',
			name: 'GeneralNoPositionList',
			component: GeneralNoPositionList
		},
		{
			path: '/GeneralWithPositionList',
			name: 'GeneralWithPositionList',
			component: GeneralWithPositionList
		},
		{
			path: '/IdCardBackList',
			name: 'IdCardBackList',
			component: IdCardBackList
		},
		{
			path: '/IdCardFrontList',
			name: 'IdCardFrontList',
			component: IdCardFrontList
		},
		{
			path: '/FunctionTestResultList',
			name: 'FunctionTestResultList',
			component: FunctionTestResultList
		},
		{
			path: '/FunctionTestList',
			name: 'FunctionTestList',
			component: FunctionTestList
		},
		{
			path: '/PerformanceResultList',
			name: 'PerformanceResultList',
			component: PerformanceResultList
		}
	]
})
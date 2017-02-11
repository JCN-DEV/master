'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('courseInfo', {
                parent: 'entity',
                url: '/course-info',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.courseInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/course-info.html',
                        controller: 'CourseInfoController'
                    },
                    'courseView@courseInfo':{
                         templateUrl: 'scripts/app/entities/CourseManagementSystem/course-dashboard.html',
                         controller: 'CourseDashboardController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                       return $translate.refresh();
                    }]
                }
            })
            .state('courseInfo.dashboard', {
                parent: 'courseInfo',
                url: '/course-dashboard',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.courseInfo.home.title'
                },
                views: {
                    'courseView@courseInfo':{
                         templateUrl: 'scripts/app/entities/CourseManagementSystem/course-dashboard.html',
                         controller: 'CourseDashboardController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        /*$translatePartialLoader.addPart('instEmployee');
                        $translatePartialLoader.addPart('empType');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('global');*/
                        return $translate.refresh();
                    }]
                }
            })
            /*.state('courseInfo.report', {
                parent: 'courseInfo',
                url: '/course-dashboard',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.courseInfo.home.title'
                },
                views: {
                    'courseView@courseInfo':{
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/course-dashboard.html',
                        controller: 'CourseDashboardController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        /!*$translatePartialLoader.addPart('instEmployee');
                         $translatePartialLoader.addPart('empType');
                         $translatePartialLoader.addPart('maritalStatus');
                         $translatePartialLoader.addPart('bloodGroup');
                         $translatePartialLoader.addPart('global');*!/
                        return $translate.refresh();
                    }]
                }
            })*/



    });

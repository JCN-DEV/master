'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('libraryInfo', {
                parent: 'entity',
                url: '/library-info',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlBookInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/library-info.html',
                        controller: 'LibraryController'
                    },
                    'libraryView@libraryInfo':{
                         templateUrl: 'scripts/app/entities/DigitalLibraryManagement/library-dashboard.html',
                         controller: 'LibraryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlFileType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('libraryInfo.dashboard', {
                parent: 'libraryInfo',
                url: '/library-dashboard',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlBookInfo.home.title'
                },
                views: {
                    'courseView@courseInfo':{
                         templateUrl: 'scripts/app/entities/DigitalLibraryManagement/library-dashboard.html',
                         controller: 'LibraryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlFileType');
                        $translatePartialLoader.addPart('empType');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('global');
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

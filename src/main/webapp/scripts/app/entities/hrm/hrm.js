'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrm-welcome', {
                parent: 'entity',
                url: '/hrmhome',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrmHome.home.generalHomeTitle'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hrm/hrm-welcome.html',
                        controller: 'ContentUploadController'
                    },

                    'hrmView@hrm': {
                        templateUrl: 'scripts/app/entities/contentUpload/contentUpload-detail.html',
                        controller: 'ContentUploadDetailController'
                    }

                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrmHome');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrm-Notice',{
                parent: 'hrm-welcome',
                url: '/notice-detail/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrmHome.home.generalHomeTitle'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hrm/hrm-Notice.html',
                        controller: 'ContentUploadController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('contentUpload');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ContentUpload', function($stateParams, ContentUpload) {
                        return ContentUpload.get({id : $stateParams.id});
                    }]
                }
            })

            .state('hrm', {
                parent: 'entity',
                url: '/hrm',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrmHome.home.generalHomeTitle',
                    displayName:'HRM'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hrm/hrm.html',
                        controller: 'hrmWelcomeHomeController'
                    },
                   'hrmManagementView@hrm':{
                        templateUrl: 'scripts/app/entities/hrm/hrm-dashboard.html',
                        controller: 'hrmHomeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrmHome');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('hrEmployeeInfo');
                        $translatePartialLoader.addPart('leftmenu');
                        $translatePartialLoader.addPart('hrEmpChildrenInfo');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }]
                }
            })

            .state('hrm.dashboard', {
                parent: 'entity',
                url: '/admin-dashboard',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.hrmHome.home.generalHomeTitle',
                    displayName:'HRM Dashboard'
                },
                views: {
                    'content@':{
                        templateUrl: 'scripts/app/entities/hrm/hr-dashboard.html',
                        controller: 'hrmHomeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrmHome');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('hrEmployeeInfo');
                        $translatePartialLoader.addPart('leftmenu');
                        $translatePartialLoader.addPart('hrEmpChildrenInfo');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }]
                }
            })

            .state('hrm.employees', {
                parent: 'hrm',
                url: '/employees',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrmHome.home.generalHomeTitle',
                    displayName:'Employee List'
                },
                views: {

                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/admin-employeelist.html',
                        controller: 'HrEmployeeInfoListController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmployeeInfo');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('hrmHome');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('leftmenu');
                        return $translate.refresh();
                    }]
                }
            })
    });

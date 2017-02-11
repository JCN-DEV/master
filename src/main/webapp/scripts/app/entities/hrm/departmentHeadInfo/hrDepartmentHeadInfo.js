'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrDepartmentHeadInfo', {
                parent: 'hrm',
                url: '/hrDepartmentHeadInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrDepartmentHeadInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/departmentHeadInfo/hrDepartmentHeadInfos.html',
                        controller: 'HrDepartmentHeadInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDepartmentHeadInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrDepartmentHeadInfo.detail', {
                parent: 'hrm',
                url: '/hrDepartmentHeadInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrDepartmentHeadInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/departmentHeadInfo/hrDepartmentHeadInfo-detail.html',
                        controller: 'HrDepartmentHeadInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDepartmentHeadInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrDepartmentHeadInfo', function($stateParams, HrDepartmentHeadInfo) {
                        return HrDepartmentHeadInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrDepartmentHeadInfo.new', {
                parent: 'hrDepartmentHeadInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/departmentHeadInfo/hrDepartmentHeadInfo-dialog.html',
                        controller: 'HrDepartmentHeadInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDepartmentHeadInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            joinDate: null,
                            endDate: null,
                            activeHead: false,
                            activeStatus: false,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('hrDepartmentHeadInfo.edit', {
                parent: 'hrDepartmentHeadInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/departmentHeadInfo/hrDepartmentHeadInfo-dialog.html',
                        controller: 'HrDepartmentHeadInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDepartmentHeadInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrDepartmentHeadInfo', function($stateParams, HrDepartmentHeadInfo) {
                        return HrDepartmentHeadInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrDepartmentHeadInfo.newdept', {
                parent: 'hrDepartmentHeadInfo',
                url: '/{deptid}/newdept',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/departmentHeadInfo/hrDepartmentHeadInfo-dialog.html',
                        controller: 'HrDepartmentHeadInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDepartmentHeadInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            joinDate: null,
                            endDate: null,
                            activeHead: false,
                            activeStatus: false,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            });
    });

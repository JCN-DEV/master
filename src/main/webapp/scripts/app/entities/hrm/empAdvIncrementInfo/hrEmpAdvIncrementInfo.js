'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpAdvIncrementInfo', {
                parent: 'hrm',
                url: '/hrEmpAdvIncrementInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpAdvIncrementInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAdvIncrementInfo/hrEmpAdvIncrementInfos.html',
                        controller: 'HrEmpAdvIncrementInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAdvIncrementInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpAdvIncrementInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpAdvIncrementInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpAdvIncrementInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAdvIncrementInfo/hrEmpAdvIncrementInfo-detail.html',
                        controller: 'HrEmpAdvIncrementInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAdvIncrementInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpAdvIncrementInfo', function($stateParams, HrEmpAdvIncrementInfo) {
                        return HrEmpAdvIncrementInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpAdvIncrementInfo.profile', {
                parent: 'hrEmpAdvIncrementInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAdvIncrementInfo/hrEmpAdvIncrementInfo-profile.html',
                        controller: 'HrEmpAdvIncrementInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAdvIncrementInfo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpAdvIncrementInfo.new', {
                parent: 'hrEmpAdvIncrementInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAdvIncrementInfo/hrEmpAdvIncrementInfo-dialog.html',
                        controller: 'HrEmpAdvIncrementInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAdvIncrementInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            postName: null,
                            purpose: null,
                            incrementAmount: null,
                            orderDate: null,
                            orderNumber: null,
                            remarks: null,
                            goDate: null,
                            goDoc: null,
                            goDocContentType: null,
                            goDocName: null,
                            logId:null,
                            logStatus:null,
                            logComments:null,
                            activeStatus: true,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('hrEmpAdvIncrementInfo.edit', {
                parent: 'hrEmpAdvIncrementInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAdvIncrementInfo/hrEmpAdvIncrementInfo-dialog.html',
                        controller: 'HrEmpAdvIncrementInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAdvIncrementInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpAdvIncrementInfo', function($stateParams, HrEmpAdvIncrementInfo) {
                        return HrEmpAdvIncrementInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpAdvIncrementInfo.delete', {
                parent: 'hrEmpAdvIncrementInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empAdvIncrementInfo/hrEmpAdvIncrementInfo-delete-dialog.html',
                        controller: 'HrEmpAdvIncrementInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpAdvIncrementInfo', function(HrEmpAdvIncrementInfo) {
                                return HrEmpAdvIncrementInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpAdvIncrementInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

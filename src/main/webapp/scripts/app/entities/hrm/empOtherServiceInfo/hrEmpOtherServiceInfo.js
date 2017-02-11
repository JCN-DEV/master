'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpOtherServiceInfo', {
                parent: 'hrm',
                url: '/hrEmpOtherServiceInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpOtherServiceInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empOtherServiceInfo/hrEmpOtherServiceInfos.html',
                        controller: 'HrEmpOtherServiceInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpOtherServiceInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpOtherServiceInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpOtherServiceInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpOtherServiceInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empOtherServiceInfo/hrEmpOtherServiceInfo-detail.html',
                        controller: 'HrEmpOtherServiceInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpOtherServiceInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpOtherServiceInfo', function($stateParams, HrEmpOtherServiceInfo) {
                        return HrEmpOtherServiceInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpOtherServiceInfo.profile', {
                parent: 'hrEmpOtherServiceInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empOtherServiceInfo/hrEmpOtherServiceInfo-profile.html',
                        controller: 'HrEmpOtherServiceInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpOtherServiceInfo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpOtherServiceInfo.new', {
                parent: 'hrEmpOtherServiceInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empOtherServiceInfo/hrEmpOtherServiceInfo-dialog.html',
                        controller: 'HrEmpOtherServiceInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpOtherServiceInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            companyName: null,
                            address: null,
                            serviceType: null,
                            position: null,
                            fromDate: null,
                            toDate: null,
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
            .state('hrEmpOtherServiceInfo.edit', {
                parent: 'hrEmpOtherServiceInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empOtherServiceInfo/hrEmpOtherServiceInfo-dialog.html',
                        controller: 'HrEmpOtherServiceInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpOtherServiceInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpOtherServiceInfo', function($stateParams, HrEmpOtherServiceInfo) {
                        return HrEmpOtherServiceInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpOtherServiceInfo.delete', {
                parent: 'hrEmpOtherServiceInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empOtherServiceInfo/hrEmpOtherServiceInfo-delete-dialog.html',
                        controller: 'HrEmpOtherServiceInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpOtherServiceInfo', function(HrEmpOtherServiceInfo) {
                                return HrEmpOtherServiceInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpOtherServiceInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

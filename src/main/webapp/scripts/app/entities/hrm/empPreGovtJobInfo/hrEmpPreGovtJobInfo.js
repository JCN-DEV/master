'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpPreGovtJobInfo', {
                parent: 'hrm',
                url: '/hrEmpPreGovtJobInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpPreGovtJobInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empPreGovtJobInfo/hrEmpPreGovtJobInfos.html',
                        controller: 'HrEmpPreGovtJobInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpPreGovtJobInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpPreGovtJobInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpPreGovtJobInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpPreGovtJobInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empPreGovtJobInfo/hrEmpPreGovtJobInfo-detail.html',
                        controller: 'HrEmpPreGovtJobInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpPreGovtJobInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpPreGovtJobInfo', function($stateParams, HrEmpPreGovtJobInfo) {
                        return HrEmpPreGovtJobInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpPreGovtJobInfo.profile', {
                parent: 'hrEmpPreGovtJobInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empPreGovtJobInfo/hrEmpPreGovtJobInfo-profile.html',
                        controller: 'HrEmpPreGovtJobInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpPreGovtJobInfo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpPreGovtJobInfo.new', {
                parent: 'hrEmpPreGovtJobInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empPreGovtJobInfo/hrEmpPreGovtJobInfo-dialog.html',
                        controller: 'HrEmpPreGovtJobInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpPreGovtJobInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            organizationName: null,
                            postName: null,
                            address: null,
                            fromDate: null,
                            toDate: null,
                            salary: null,
                            comments: null,
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
            .state('hrEmpPreGovtJobInfo.edit', {
                parent: 'hrEmpPreGovtJobInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empPreGovtJobInfo/hrEmpPreGovtJobInfo-dialog.html',
                        controller: 'HrEmpPreGovtJobInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpPreGovtJobInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpPreGovtJobInfo', function($stateParams, HrEmpPreGovtJobInfo) {
                        return HrEmpPreGovtJobInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpPreGovtJobInfo.delete', {
                parent: 'hrEmpPreGovtJobInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empPreGovtJobInfo/hrEmpPreGovtJobInfo-delete-dialog.html',
                        controller: 'HrEmpPreGovtJobInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpPreGovtJobInfo', function(HrEmpPreGovtJobInfo) {
                                return HrEmpPreGovtJobInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpPreGovtJobInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

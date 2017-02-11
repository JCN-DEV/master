'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpGovtDuesInfo', {
                parent: 'hrm',
                url: '/hrEmpGovtDuesInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpGovtDuesInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empGovtDuesInfo/hrEmpGovtDuesInfos.html',
                        controller: 'HrEmpGovtDuesInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpGovtDuesInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpGovtDuesInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpGovtDuesInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpGovtDuesInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empGovtDuesInfo/hrEmpGovtDuesInfo-detail.html',
                        controller: 'HrEmpGovtDuesInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpGovtDuesInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpGovtDuesInfo', function($stateParams, HrEmpGovtDuesInfo) {
                        return HrEmpGovtDuesInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpGovtDuesInfo.profile', {
                parent: 'hrEmpGovtDuesInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empGovtDuesInfo/hrEmpGovtDuesInfo-profile.html',
                        controller: 'HrEmpGovtDuesInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpGovtDuesInfo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpGovtDuesInfo.new', {
                parent: 'hrEmpGovtDuesInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empGovtDuesInfo/hrEmpGovtDuesInfo-dialog.html',
                        controller: 'HrEmpGovtDuesInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpGovtDuesInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            description: null,
                            dueAmount: null,
                            claimerAuthority: null,
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
            .state('hrEmpGovtDuesInfo.edit', {
                parent: 'hrEmpGovtDuesInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empGovtDuesInfo/hrEmpGovtDuesInfo-dialog.html',
                        controller: 'HrEmpGovtDuesInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpGovtDuesInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpGovtDuesInfo', function($stateParams, HrEmpGovtDuesInfo) {
                        return HrEmpGovtDuesInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpGovtDuesInfo.delete', {
                parent: 'hrEmpGovtDuesInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empGovtDuesInfo/hrEmpGovtDuesInfo-delete-dialog.html',
                        controller: 'HrEmpGovtDuesInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpGovtDuesInfo', function(HrEmpGovtDuesInfo) {
                                return HrEmpGovtDuesInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpGovtDuesInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

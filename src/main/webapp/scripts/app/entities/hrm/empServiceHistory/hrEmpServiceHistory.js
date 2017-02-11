'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpServiceHistory', {
                parent: 'hrm',
                url: '/hrEmpServiceHistorys',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpServiceHistory.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empServiceHistory/hrEmpServiceHistorys.html',
                        controller: 'HrEmpServiceHistoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpServiceHistory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpServiceHistory.detail', {
                parent: 'hrm',
                url: '/hrEmpServiceHistory/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpServiceHistory.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empServiceHistory/hrEmpServiceHistory-detail.html',
                        controller: 'HrEmpServiceHistoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpServiceHistory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpServiceHistory', function($stateParams, HrEmpServiceHistory) {
                        return HrEmpServiceHistory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpServiceHistory.profile', {
                parent: 'hrEmpServiceHistory',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empServiceHistory/hrEmpServiceHistory-profile.html',
                        controller: 'HrEmpServiceHistoryProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpServiceHistory');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpServiceHistory.new', {
                parent: 'hrEmpServiceHistory',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empServiceHistory/hrEmpServiceHistory-dialog.html',
                        controller: 'HrEmpServiceHistoryDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpServiceHistory');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            serviceDate: null,
                            gazettedDate: null,
                            encadrementDate: null,
                            nationalSeniority: null,
                            cadreNumber: null,
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
            .state('hrEmpServiceHistory.edit', {
                parent: 'hrEmpServiceHistory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empServiceHistory/hrEmpServiceHistory-dialog.html',
                        controller: 'HrEmpServiceHistoryDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpServiceHistory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpServiceHistory', function($stateParams, HrEmpServiceHistory) {
                        return HrEmpServiceHistory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpServiceHistory.delete', {
                parent: 'hrEmpServiceHistory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empServiceHistory/hrEmpServiceHistory-delete-dialog.html',
                        controller: 'HrEmpServiceHistoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpServiceHistory', function(HrEmpServiceHistory) {
                                return HrEmpServiceHistory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpServiceHistory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('mpoSalaryFlow', {
                parent: 'entity',
                url: '/mpoSalaryFlows',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.mpoSalaryFlow.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mpoSalaryFlow/salaryFlowDashboard.html',
                        controller: 'MpoSalaryFlowController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoSalaryFlow');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mpoSalaryFlow.detail', {
                parent: 'entity',
                url: '/mpoSalaryFlow/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.mpoSalaryFlow.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mpoSalaryFlow/mpoSalaryFlow-detail.html',
                        controller: 'MpoSalaryFlowDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoSalaryFlow');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoSalaryFlow', function($stateParams, MpoSalaryFlow) {
                        return MpoSalaryFlow.get({id : $stateParams.id});
                    }]
                }
            })
            .state('mpoSalaryFlow.new', {
                parent: 'mpoSalaryFlow',
                url: '/new',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/mpoSalaryFlow/mpoSalaryFlow-dialog.html',
                        controller: 'MpoSalaryFlowDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    reportName: null,
                                    urls: null,
                                    forwardTo: null,
                                    forwardFrom: null,
                                    forwardToRole: null,
                                    status: null,
                                    approveStatus: null,
                                    userLock: null,
                                    levels: null,
                                    dteStatus: null,
                                    dteId: null,
                                    dteApproveDate: null,
                                    ministryStatus: null,
                                    ministryId: null,
                                    ministryApproveDate: null,
                                    agStatus: null,
                                    agId: null,
                                    agApproveDate: null,
                                    dteComments: null,
                                    ministryComments: null,
                                    agComments: null,
                                    comments: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('mpoSalaryFlow', null, { reload: true });
                    }, function() {
                        $state.go('mpoSalaryFlow');
                    })
                }]
            })
            .state('mpoSalaryFlow.edit', {
                parent: 'mpoSalaryFlow',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/mpoSalaryFlow/mpoSalaryFlow-dialog.html',
                        controller: 'MpoSalaryFlowDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MpoSalaryFlow', function(MpoSalaryFlow) {
                                return MpoSalaryFlow.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mpoSalaryFlow', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

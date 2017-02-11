'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prlEmpPaymentStopInfo', {
                parent: 'payroll',
                url: '/prlEmpPaymentStopInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlEmpPaymentStopInfo.home.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/empPaymentStopInfo/prlEmpPaymentStopInfos.html',
                        controller: 'PrlEmpPaymentStopInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlEmpPaymentStopInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prlEmpPaymentStopInfo.detail', {
                parent: 'payroll',
                url: '/prlEmpPaymentStopInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlEmpPaymentStopInfo.detail.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/empPaymentStopInfo/prlEmpPaymentStopInfo-detail.html',
                        controller: 'PrlEmpPaymentStopInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlEmpPaymentStopInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlEmpPaymentStopInfo', function($stateParams, PrlEmpPaymentStopInfo) {
                        return PrlEmpPaymentStopInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlEmpPaymentStopInfo.new', {
                parent: 'prlEmpPaymentStopInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/empPaymentStopInfo/prlEmpPaymentStopInfo-dialog.html',
                        controller: 'PrlEmpPaymentStopInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlEmpPaymentStopInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            effectedDateFrom: null,
                            effectedDateTo: null,
                            stopActionType: null,
                            activeStatus: true,
                            comments: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('prlEmpPaymentStopInfo.edit', {
                parent: 'prlEmpPaymentStopInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/empPaymentStopInfo/prlEmpPaymentStopInfo-dialog.html',
                        controller: 'PrlEmpPaymentStopInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlEmpPaymentStopInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlEmpPaymentStopInfo', function($stateParams, PrlEmpPaymentStopInfo) {
                        return PrlEmpPaymentStopInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlEmpPaymentStopInfo.delete', {
                parent: 'prlEmpPaymentStopInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payroll/empPaymentStopInfo/prlEmpPaymentStopInfo-delete-dialog.html',
                        controller: 'PrlEmpPaymentStopInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrlEmpPaymentStopInfo', function(PrlEmpPaymentStopInfo) {
                                return PrlEmpPaymentStopInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prlEmpPaymentStopInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

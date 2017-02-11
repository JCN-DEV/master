'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prlAllowDeductInfo', {
                parent: 'payroll',
                url: '/prlAllowDeductInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlAllowDeductInfo.home.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/allowDeductInfo/prlAllowDeductInfos.html',
                        controller: 'PrlAllowDeductInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlAllowDeductInfo');
                        $translatePartialLoader.addPart('allowanceDeductionType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prlAllowDeductInfo.detail', {
                parent: 'payroll',
                url: '/prlAllowDeductInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlAllowDeductInfo.detail.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/allowDeductInfo/prlAllowDeductInfo-detail.html',
                        controller: 'PrlAllowDeductInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlAllowDeductInfo');
                        $translatePartialLoader.addPart('allowanceDeductionType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlAllowDeductInfo', function($stateParams, PrlAllowDeductInfo) {
                        return PrlAllowDeductInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlAllowDeductInfo.new', {
                parent: 'prlAllowDeductInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/allowDeductInfo/prlAllowDeductInfo-dialog.html',
                        controller: 'PrlAllowDeductInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlAllowDeductInfo');
                        $translatePartialLoader.addPart('allowanceDeductionType');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            name: null,
                            allowDeducType: null,
                            allowCategory: null,
                            allowDeducCode: null,
                            description: null,
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
            .state('prlAllowDeductInfo.edit', {
                parent: 'prlAllowDeductInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/allowDeductInfo/prlAllowDeductInfo-dialog.html',
                        controller: 'PrlAllowDeductInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlAllowDeductInfo');
                        $translatePartialLoader.addPart('allowanceDeductionType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlAllowDeductInfo', function($stateParams, PrlAllowDeductInfo) {
                        return PrlAllowDeductInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlAllowDeductInfo.delete', {
                parent: 'prlAllowDeductInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payroll/allowDeductInfo/prlAllowDeductInfo-delete-dialog.html',
                        controller: 'PrlAllowDeductInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrlAllowDeductInfo', function(PrlAllowDeductInfo) {
                                return PrlAllowDeductInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prlAllowDeductInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

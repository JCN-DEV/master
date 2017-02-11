'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prlEmpGenSalDetailInfo', {
                parent: 'payroll',
                url: '/prlEmpGenSalDetailInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlEmpGenSalDetailInfo.home.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/empGenSalDetailInfo/prlEmpGenSalDetailInfos.html',
                        controller: 'PrlEmpGenSalDetailInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlEmpGenSalDetailInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prlEmpGenSalDetailInfo.detail', {
                parent: 'payroll',
                url: '/prlEmpGenSalDetailInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlEmpGenSalDetailInfo.detail.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/empGenSalDetailInfo/prlEmpGenSalDetailInfo-detail.html',
                        controller: 'PrlEmpGenSalDetailInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlEmpGenSalDetailInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlEmpGenSalDetailInfo', function($stateParams, PrlEmpGenSalDetailInfo) {
                        return PrlEmpGenSalDetailInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlEmpGenSalDetailInfo.new', {
                parent: 'prlEmpGenSalDetailInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/empGenSalDetailInfo/prlEmpGenSalDetailInfo-dialog.html',
                        controller: 'PrlEmpGenSalDetailInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlEmpGenSalDetailInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            allowDeducType: null,
                            allowDeducId: null,
                            allowDeducName: null,
                            allowDeducValue: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('prlEmpGenSalDetailInfo.edit', {
                parent: 'prlEmpGenSalDetailInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/empGenSalDetailInfo/prlEmpGenSalDetailInfo-dialog.html',
                        controller: 'PrlEmpGenSalDetailInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlEmpGenSalDetailInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlEmpGenSalDetailInfo', function($stateParams, PrlEmpGenSalDetailInfo) {
                        return PrlEmpGenSalDetailInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlEmpGenSalDetailInfo.delete', {
                parent: 'prlEmpGenSalDetailInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payroll/empGenSalDetailInfo/prlEmpGenSalDetailInfo-delete-dialog.html',
                        controller: 'PrlEmpGenSalDetailInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrlEmpGenSalDetailInfo', function(PrlEmpGenSalDetailInfo) {
                                return PrlEmpGenSalDetailInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prlEmpGenSalDetailInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

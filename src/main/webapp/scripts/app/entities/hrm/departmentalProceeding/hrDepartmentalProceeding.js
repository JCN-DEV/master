'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrDepartmentalProceeding', {
                parent: 'hrm',
                url: '/hrDepartmentalProceedings',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrDepartmentalProceeding.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/departmentalProceeding/hrDepartmentalProceedings.html',
                        controller: 'HrDepartmentalProceedingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDepartmentalProceeding');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrDepartmentalProceeding.detail', {
                parent: 'hrm',
                url: '/hrDepartmentalProceeding/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrDepartmentalProceeding.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/departmentalProceeding/hrDepartmentalProceeding-detail.html',
                        controller: 'HrDepartmentalProceedingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDepartmentalProceeding');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrDepartmentalProceeding', function($stateParams, HrDepartmentalProceeding) {
                        return HrDepartmentalProceeding.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrDepartmentalProceeding.new', {
                parent: 'hrDepartmentalProceeding',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/departmentalProceeding/hrDepartmentalProceeding-dialog.html',
                        controller: 'HrDepartmentalProceedingDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDepartmentalProceeding');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            crimeNature: null,
                            nature: null,
                            amount: null,
                            formDate: null,
                            toDate: null,
                            period: null,
                            dudakCaseDetail: null,
                            dudakPunishment: null,
                            goDate: null,
                            goDoc: null,
                            goDocContentType: null,
                            goDocName: null,
                            remarks: null,
                            logId: null,
                            logStatus: null,
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
            .state('hrDepartmentalProceeding.edit', {
                parent: 'hrDepartmentalProceeding',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/departmentalProceeding/hrDepartmentalProceeding-dialog.html',
                        controller: 'HrDepartmentalProceedingDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrDepartmentalProceeding');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrDepartmentalProceeding', function($stateParams, HrDepartmentalProceeding) {
                        return HrDepartmentalProceeding.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrDepartmentalProceeding.delete', {
                parent: 'hrDepartmentalProceeding',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/departmentalProceeding/hrDepartmentalProceeding-delete-dialog.html',
                        controller: 'HrDepartmentalProceedingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrDepartmentalProceeding', function(HrDepartmentalProceeding) {
                                return HrDepartmentalProceeding.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrDepartmentalProceeding', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

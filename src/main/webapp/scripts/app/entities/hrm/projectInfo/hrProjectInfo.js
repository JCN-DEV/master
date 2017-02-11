'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrProjectInfo', {
                parent: 'hrm',
                url: '/hrProjectInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrProjectInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/projectInfo/hrProjectInfos.html',
                        controller: 'HrProjectInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrProjectInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrProjectInfo.detail', {
                parent: 'hrm',
                url: '/hrProjectInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrProjectInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/projectInfo/hrProjectInfo-detail.html',
                        controller: 'HrProjectInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrProjectInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrProjectInfo', function($stateParams, HrProjectInfo) {
                        return HrProjectInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrProjectInfo.new', {
                parent: 'hrProjectInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/projectInfo/hrProjectInfo-dialog.html',
                        controller: 'HrProjectInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrProjectInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            projectName: null,
                            projectDetail: null,
                            directorName: null,
                            startDate: null,
                            endDate: null,
                            projectValue: null,
                            projectStatus: null,
                            joiningDatePd: null,
                            phoneNumber: null,
                            emailAddress: null,
                            projectLocation: null,
                            estTotalCostOriginal: null,
                            estGobCostOriginal: null,
                            estPaCostOriginal: null,
                            estTotalCostRevised: null,
                            estGobCostRevised: null,
                            estPaCostRevised: null,
                            cumulProgressLastJune: null,
                            currentYearTotalAlloc: null,
                            currentYearGobAlloc: null,
                            currentYearPaAlloc: null,
                            currentMonthTotalProg: null,
                            currentMonthGobProg: null,
                            currentMonthPaProg: null,
                            currentYearProgress: null,
                            currentYearRelease: null,
                            projectProgressTotal: null,
                            projectProgressRevenue: null,
                            projectProgressCapital: null,
                            projectManpower: null,

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
            .state('hrProjectInfo.edit', {
                parent: 'hrProjectInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/projectInfo/hrProjectInfo-dialog.html',
                        controller: 'HrProjectInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrProjectInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrProjectInfo', function($stateParams, HrProjectInfo) {
                        return HrProjectInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrProjectInfo.delete', {
                parent: 'hrProjectInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/projectInfo/hrProjectInfo-delete-dialog.html',
                        controller: 'HrProjectInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrProjectInfo', function(HrProjectInfo) {
                                return HrProjectInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrProjectInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

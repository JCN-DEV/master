'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpAcrInfo', {
                parent: 'hrm',
                url: '/hrEmpAcrInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpAcrInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAcrInfo/hrEmpAcrInfos.html',
                        controller: 'HrEmpAcrInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAcrInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpAcrInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpAcrInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpAcrInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAcrInfo/hrEmpAcrInfo-detail.html',
                        controller: 'HrEmpAcrInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAcrInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpAcrInfo', function($stateParams, HrEmpAcrInfo) {
                        return HrEmpAcrInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpAcrInfo.new', {
                parent: 'hrEmpAcrInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAcrInfo/hrEmpAcrInfo-dialog.html',
                        controller: 'HrEmpAcrInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAcrInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            acrYear: null,
                            totalMarks: null,
                            overallEvaluation: null,
                            promotionStatus: null,
                            acrDate: null,
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
            .state('hrEmpAcrInfo.profile', {
                parent: 'hrEmpAcrInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAcrInfo/hrEmpAcrInfo-profile.html',
                        controller: 'HrEmpAcrInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAcrInfo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpAcrInfo.edit', {
                parent: 'hrEmpAcrInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAcrInfo/hrEmpAcrInfo-dialog.html',
                        controller: 'HrEmpAcrInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAcrInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpAcrInfo', function($stateParams, HrEmpAcrInfo) {
                        return HrEmpAcrInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpAcrInfo.delete', {
                parent: 'hrEmpAcrInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empAcrInfo/hrEmpAcrInfo-delete-dialog.html',
                        controller: 'HrEmpAcrInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpAcrInfo', function(HrEmpAcrInfo) {
                                return HrEmpAcrInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpAcrInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

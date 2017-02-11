'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpForeignTourInfo', {
                parent: 'hrm',
                url: '/hrEmpForeignTourInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpForeignTourInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empForeignTourInfo/hrEmpForeignTourInfos.html',
                        controller: 'HrEmpForeignTourInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpForeignTourInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpForeignTourInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpForeignTourInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpForeignTourInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empForeignTourInfo/hrEmpForeignTourInfo-detail.html',
                        controller: 'HrEmpForeignTourInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpForeignTourInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpForeignTourInfo', function($stateParams, HrEmpForeignTourInfo) {
                        return HrEmpForeignTourInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpForeignTourInfo.profile', {
                parent: 'hrEmpForeignTourInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empForeignTourInfo/hrEmpForeignTourInfo-profile.html',
                        controller: 'HrEmpForeignTourInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpForeignTourInfo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpForeignTourInfo.new', {
                parent: 'hrEmpForeignTourInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empForeignTourInfo/hrEmpForeignTourInfo-dialog.html',
                        controller: 'HrEmpForeignTourInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpForeignTourInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            purpose: null,
                            fromDate: null,
                            toDate: null,
                            countryName: null,
                            officeOrderNumber: null,
                            fundSource: null,
                            goDate: null,
                            goDoc: null,
                            goDocContentType: null,
                            goDocName: null,
                            goNumber: null,
                            officeOrderDate: null,
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
            .state('hrEmpForeignTourInfo.edit', {
                parent: 'hrEmpForeignTourInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empForeignTourInfo/hrEmpForeignTourInfo-dialog.html',
                        controller: 'HrEmpForeignTourInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpForeignTourInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpForeignTourInfo', function($stateParams, HrEmpForeignTourInfo) {
                        return HrEmpForeignTourInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpForeignTourInfo.delete', {
                parent: 'hrEmpForeignTourInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empForeignTourInfo/hrEmpForeignTourInfo-delete-dialog.html',
                        controller: 'HrEmpForeignTourInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpForeignTourInfo', function(HrEmpForeignTourInfo) {
                                return HrEmpForeignTourInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpForeignTourInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

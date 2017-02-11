'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpWorkAreaDtlInfo', {
                parent: 'hrm',
                url: '/hrEmpWorkAreaDtlInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpWorkAreaDtlInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/workAreaDtlInfo/hrEmpWorkAreaDtlInfos.html',
                        controller: 'HrEmpWorkAreaDtlInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpWorkAreaDtlInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpWorkAreaDtlInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpWorkAreaDtlInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpWorkAreaDtlInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/workAreaDtlInfo/hrEmpWorkAreaDtlInfo-detail.html',
                        controller: 'HrEmpWorkAreaDtlInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpWorkAreaDtlInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpWorkAreaDtlInfo', function($stateParams, HrEmpWorkAreaDtlInfo) {
                        return HrEmpWorkAreaDtlInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpWorkAreaDtlInfo.new', {
                parent: 'hrEmpWorkAreaDtlInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/workAreaDtlInfo/hrEmpWorkAreaDtlInfo-dialog.html',
                        controller: 'HrEmpWorkAreaDtlInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpWorkAreaDtlInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            name: null,
                            establishmentDate: null,
                            contactNumber: null,
                            address: null,
                            telephoneNumber: null,
                            faxNumber: null,
                            emailAddress: null,
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
            .state('hrEmpWorkAreaDtlInfo.edit', {
                parent: 'hrEmpWorkAreaDtlInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/workAreaDtlInfo/hrEmpWorkAreaDtlInfo-dialog.html',
                        controller: 'HrEmpWorkAreaDtlInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpWorkAreaDtlInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpWorkAreaDtlInfo', function($stateParams, HrEmpWorkAreaDtlInfo) {
                        return HrEmpWorkAreaDtlInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpWorkAreaDtlInfo.delete', {
                parent: 'hrEmpWorkAreaDtlInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/workAreaDtlInfo/hrEmpWorkAreaDtlInfo-delete-dialog.html',
                        controller: 'HrEmpWorkAreaDtlInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpWorkAreaDtlInfo', function(HrEmpWorkAreaDtlInfo) {
                                return HrEmpWorkAreaDtlInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpWorkAreaDtlInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

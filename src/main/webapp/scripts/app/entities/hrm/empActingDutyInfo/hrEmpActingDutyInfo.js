'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpActingDutyInfo', {
                parent: 'hrm',
                url: '/hrEmpActingDutyInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpActingDutyInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empActingDutyInfo/hrEmpActingDutyInfos.html',
                        controller: 'HrEmpActingDutyInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpActingDutyInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpActingDutyInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpActingDutyInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpActingDutyInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empActingDutyInfo/hrEmpActingDutyInfo-detail.html',
                        controller: 'HrEmpActingDutyInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpActingDutyInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpActingDutyInfo', function($stateParams, HrEmpActingDutyInfo) {
                        return HrEmpActingDutyInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpActingDutyInfo.profile', {
                parent: 'hrEmpActingDutyInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },

                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empActingDutyInfo/hrEmpActingDutyInfo-profile.html',
                        controller: 'HrEmpActingDutyInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpActingDutyInfo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpActingDutyInfo.new', {
                parent: 'hrEmpActingDutyInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },

                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empActingDutyInfo/hrEmpActingDutyInfo-dialog.html',
                        controller: 'HrEmpActingDutyInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpActingDutyInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            toInstitution: null,
                            designation: null,
                            toDepartment: null,
                            fromDate: null,
                            toDate: null,
                            officeOrderNumber: null,
                            officeOrderDate: null,
                            workOnActingDuty: null,
                            comments: null,
                            goDate: null,
                            goDoc: null,
                            goDocContentType: null,
                            goDocName: null,
                            goNumber: null,
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
            .state('hrEmpActingDutyInfo.edit', {
                parent: 'hrEmpActingDutyInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empActingDutyInfo/hrEmpActingDutyInfo-dialog.html',
                        controller: 'HrEmpActingDutyInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpActingDutyInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpActingDutyInfo', function($stateParams, HrEmpActingDutyInfo) {
                        return HrEmpActingDutyInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpActingDutyInfo.delete', {
                parent: 'hrEmpActingDutyInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empActingDutyInfo/hrEmpActingDutyInfo-delete-dialog.html',
                        controller: 'HrEmpActingDutyInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpActingDutyInfo', function(HrEmpActingDutyInfo) {
                                return HrEmpActingDutyInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpActingDutyInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpBankAccountInfo', {
                parent: 'hrm',
                url: '/hrEmpBankAccountInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpBankAccountInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/bankAccountInfo/hrEmpBankAccountInfos.html',
                        controller: 'HrEmpBankAccountInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpBankAccountInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpBankAccountInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpBankAccountInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpBankAccountInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/bankAccountInfo/hrEmpBankAccountInfo-detail.html',
                        controller: 'HrEmpBankAccountInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpBankAccountInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpBankAccountInfo', function($stateParams, HrEmpBankAccountInfo) {
                        return HrEmpBankAccountInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpBankAccountInfo.profile', {
                parent: 'hrEmpBankAccountInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER']
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/bankAccountInfo/hrEmpBankAccountInfo-profile.html',
                        controller: 'HrEmpBankAccountInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpBankAccountInfo');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpBankAccountInfo.new', {
                parent: 'hrEmpBankAccountInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER']
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/bankAccountInfo/hrEmpBankAccountInfo-dialog.html',
                        controller: 'HrEmpBankAccountInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpBankAccountInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            accountName: null,
                            accountNumber: null,
                            branchName: null,
                            description: null,
                            salaryAccount: false,
                            activeStatus: true,
                            logId: null,
                            logStatus: null,
                            logComments: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('hrEmpBankAccountInfo.edit', {
                parent: 'hrEmpBankAccountInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER']
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/bankAccountInfo/hrEmpBankAccountInfo-dialog.html',
                        controller: 'HrEmpBankAccountInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpBankAccountInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpBankAccountInfo', function($stateParams, HrEmpBankAccountInfo) {
                        return HrEmpBankAccountInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpBankAccountInfo.delete', {
                parent: 'hrEmpBankAccountInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/bankAccountInfo/hrEmpBankAccountInfo-delete-dialog.html',
                        controller: 'HrEmpBankAccountInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpBankAccountInfo', function(HrEmpBankAccountInfo) {
                                return HrEmpBankAccountInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpBankAccountInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

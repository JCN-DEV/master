'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('smsServiceDepartment', {
                parent: 'sms',
                url: '/smsServiceDepartments',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.smsServiceDepartment.home.title'
                },
                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceDepartment/smsServiceDepartments.html',
                        controller: 'SmsServiceDepartmentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceDepartment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('smsServiceDepartment.detail', {
                parent: 'smsServiceDepartment',
                url: '/smsServiceDepartment/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.smsServiceDepartment.detail.title'
                },
                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceDepartment/smsServiceDepartment-detail.html',
                        controller: 'SmsServiceDepartmentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceDepartment');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SmsServiceDepartment', function($stateParams, SmsServiceDepartment) {
                        return SmsServiceDepartment.get({id : $stateParams.id});
                    }]
                }
            })
            .state('smsServiceDepartment.new', {
                parent: 'smsServiceDepartment',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceDepartment/smsServiceDepartment-dialog.html',
                        controller: 'SmsServiceDepartmentDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceDepartment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            name: null,
                            description: null,
                            activeStatus: null,
                            id: null
                        };
                    }
                }
            })
            .state('smsServiceDepartment.edit', {
                parent: 'smsServiceDepartment',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceDepartment/smsServiceDepartment-dialog.html',
                        controller: 'SmsServiceDepartmentDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceDepartment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['SmsServiceDepartment','$stateParams', function(SmsServiceDepartment,$stateParams) {
                        return SmsServiceDepartment.get({id : $stateParams.id});
                    }]
                }

            })
            .state('smsServiceDepartment.delete', {
                parent: 'smsServiceDepartment',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/sms/serviceDepartment/smsServiceDepartment-delete-dialog.html',
                        controller: 'SmsServiceDepartmentDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SmsServiceDepartment', function(SmsServiceDepartment) {
                                return SmsServiceDepartment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('smsServiceDepartment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

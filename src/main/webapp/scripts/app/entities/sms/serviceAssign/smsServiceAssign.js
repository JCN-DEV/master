'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('smsServiceAssign', {
                parent: 'sms',
                url: '/smsServiceAssigns',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.smsServiceAssign.home.title'
                },
                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceAssign/smsServiceAssigns.html',
                        controller: 'SmsServiceAssignController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceAssign');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('smsServiceAssign.detail', {
                parent: 'smsServiceAssign',
                url: '/smsServiceAssign/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.smsServiceAssign.detail.title'
                },
                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceAssign/smsServiceAssign-detail.html',
                        controller: 'SmsServiceAssignDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceAssign');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SmsServiceAssign', function($stateParams, SmsServiceAssign) {
                        return SmsServiceAssign.get({id : $stateParams.id});
                    }]
                }
            })
            .state('smsServiceAssign.new', {
                parent: 'smsServiceAssign',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceAssign/smsServiceAssign-dialog.html',
                        controller: 'SmsServiceAssignDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceAssign');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            activeStatus: null,
                            id: null
                        };
                    }
                }

            })
            .state('smsServiceAssign.edit', {
                parent: 'smsServiceAssign',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },

                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceAssign/smsServiceAssign-dialog.html',
                        controller: 'SmsServiceAssignDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceAssign');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['SmsServiceAssign','$stateParams', function(SmsServiceAssign,$stateParams) {
                        return SmsServiceAssign.get({id : $stateParams.id});
                    }]
                }

            })
            .state('smsServiceAssign.delete', {
                parent: 'smsServiceAssign',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/sms/serviceAssign/smsServiceAssign-delete-dialog.html',
                        controller: 'SmsServiceAssignDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SmsServiceAssign', function(SmsServiceAssign) {
                                return SmsServiceAssign.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('smsServiceAssign', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

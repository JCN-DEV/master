'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('smsServiceComplaint', {
                parent: 'sms',
                url: '/smsServiceComplaints',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.smsServiceComplaint.home.title'
                },
                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceComplaint/smsServiceComplaints.html',
                        controller: 'SmsServiceComplaintController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceComplaint');
                        $translatePartialLoader.addPart('priority');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('smsServiceComplaint.detail', {
                parent: 'smsServiceComplaint',
                url: '/smsServiceComplaint/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.smsServiceComplaint.detail.title'
                },
                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceComplaint/smsServiceComplaint-detail.html',
                        controller: 'SmsServiceComplaintDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceComplaint');
                        $translatePartialLoader.addPart('priority');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SmsServiceComplaint', function($stateParams, SmsServiceComplaint) {
                        return SmsServiceComplaint.get({id : $stateParams.id});
                    }]
                }
            })
            .state('smsServiceComplaint.complaint', {
                parent: 'sms',
                url: '/complaint',
                data: {
                    authorities: [],
                },

                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sms/serviceComplaint/smsServiceComplaint-dialog.html',
                        controller: 'SmsServiceComplaintDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceComplaint');
                        $translatePartialLoader.addPart('priority');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            previousCode: null,
                            priority: null,
                            complaintName: null,
                            fullName: null,
                            emailAddress: null,
                            contactNumber: null,
                            description: null,
                            complaintDoc: null,
                            complaintDocContentType: null,
                            activeStatus: null,
                            id: null
                        };
                    }
                }
            })
            .state('smsServiceComplaint.edit', {
                parent: 'smsServiceComplaint',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'smsManagementView@sms': {
                        templateUrl: 'scripts/app/entities/sms/serviceComplaint/smsServiceComplaint-dialog.html',
                        controller: 'SmsServiceComplaintDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceComplaint');
                        $translatePartialLoader.addPart('priority');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['SmsServiceComplaint','$stateParams', function(SmsServiceComplaint,$stateParams) {
                        return SmsServiceComplaint.get({id : $stateParams.id});
                    }]
                }

            })
            .state('smsServiceComplaint.delete', {
                parent: 'smsServiceComplaint',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/sms/serviceComplaint/smsServiceComplaint-delete-dialog.html',
                        controller: 'SmsServiceComplaintDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SmsServiceComplaint', function(SmsServiceComplaint) {
                                return SmsServiceComplaint.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('smsServiceComplaint', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('smsServiceComplaint.preview', {
                parent: 'smsServiceComplaint.detail',
                url: '/{id}/preview',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/sms/serviceComplaint/smsServiceComplaint-preview-dialog.html',
                        controller: 'SmsServiceComplaintPreviewController',
                        size: 'md',
                        resolve: {
                            entity: ['SmsServiceComplaint', function(SmsServiceComplaint) {
                                return SmsServiceComplaint.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('smsServiceComplaint', null, { reload: true });
                        }, function() {
                            $state.go('^');
                        })
                }]
            });;
    });

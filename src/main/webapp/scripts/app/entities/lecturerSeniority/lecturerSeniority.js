'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('lecturerSeniority', {
                parent: 'entity',
                url: '/lecturerSenioritys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.lecturerSeniority.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lecturerSeniority/lecturerSenioritys.html',
                        controller: 'LecturerSeniorityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lecturerSeniority');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('lecturerSeniority.detail', {
                parent: 'entity',
                url: '/lecturerSeniority/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.lecturerSeniority.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lecturerSeniority/lecturerSeniority-detail.html',
                        controller: 'LecturerSeniorityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lecturerSeniority');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LecturerSeniority', function($stateParams, LecturerSeniority) {
                        return LecturerSeniority.get({id : $stateParams.id});
                    }]
                }
            })
            .state('lecturerSeniority.new', {
                parent: 'lecturerSeniority',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/lecturerSeniority/lecturerSeniority-dialog.html',
                        controller: 'LecturerSeniorityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    serial: null,
                                    name: null,
                                    subject: null,
                                    firstMPOEnlistingDate: null,
                                    joiningDateAsLecturer: null,
                                    dob: null,
                                    remarks: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('lecturerSeniority', null, { reload: true });
                    }, function() {
                        $state.go('lecturerSeniority');
                    })
                }]
            })
            .state('lecturerSeniority.edit', {
                parent: 'lecturerSeniority',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/lecturerSeniority/lecturerSeniority-dialog.html',
                        controller: 'LecturerSeniorityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LecturerSeniority', function(LecturerSeniority) {
                                return LecturerSeniority.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('lecturerSeniority', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('lecturerSeniority.delete', {
                parent: 'lecturerSeniority',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/lecturerSeniority/lecturerSeniority-delete-dialog.html',
                        controller: 'LecturerSeniorityDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['LecturerSeniority', function(LecturerSeniority) {
                                return LecturerSeniority.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('lecturerSeniority', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('applicantEducation', {
                parent: 'entity',
                url: '/applicantEducations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.applicantEducation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/applicantEducation/applicantEducations.html',
                        controller: 'ApplicantEducationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('applicantEducation');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('applicantEducation.detail', {
                parent: 'entity',
                url: '/applicantEducation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.applicantEducation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/applicantEducation/applicantEducation-detail.html',
                        controller: 'ApplicantEducationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('applicantEducation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ApplicantEducation', function($stateParams, ApplicantEducation) {
                        return ApplicantEducation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('applicantEducation.new', {
                parent: 'applicantEducation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/applicantEducation/applicantEducation-dialog.html',
                        controller: 'ApplicantEducationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    applicantGroup: null,
                                    gpa: null,
                                    examYear: null,
                                    examResultDate: null,
                                    remarks: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('applicantEducation', null, { reload: true });
                    }, function() {
                        $state.go('applicantEducation');
                    })
                }]
            })
            .state('applicantEducation.edit', {
                parent: 'applicantEducation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/applicantEducation/applicantEducation-dialog.html',
                        controller: 'ApplicantEducationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ApplicantEducation', function(ApplicantEducation) {
                                return ApplicantEducation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('applicantEducation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('applicantEducation.delete', {
                parent: 'applicantEducation',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/applicantEducation/applicantEducation-delete-dialog.html',
                        controller: 'ApplicantEducationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ApplicantEducation', function(ApplicantEducation) {
                                return ApplicantEducation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('applicantEducation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

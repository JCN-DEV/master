'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('educationalQualification', {
                parent: 'entity',
                url: '/educationalQualifications',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.educationalQualification.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/educationalQualification/educationalQualifications.html',
                        controller: 'EducationalQualificationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('educationalQualification');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('educationalQualification.detail', {
                parent: 'entity',
                url: '/educationalQualification/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.educationalQualification.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/educationalQualification/educationalQualification-detail.html',
                        controller: 'EducationalQualificationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('educationalQualification');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EducationalQualification', function($stateParams, EducationalQualification) {
                        return EducationalQualification.get({id : $stateParams.id});
                    }]
                }
            })
            .state('educationalQualification.new', {
                parent: 'educationalQualification',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.educationalQualification.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/educationalQualification/educationalQualification-form.html',
                        controller: 'EducationalQualificationFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('educationalQualification');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EducationalQualification', function($stateParams, EducationalQualification) {
                        return EducationalQualification.get({id : $stateParams.id});
                    }]
                }

            })
            .state('educationalQualification.edit', {
                parent: 'educationalQualification',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.educationalQualification.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/educationalQualification/educationalQualification-form.html',
                        controller: 'EducationalQualificationFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('educationalQualification');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EducationalQualification', function($stateParams, EducationalQualification) {
                        return EducationalQualification.get({id : $stateParams.id});
                    }]
                }

            })
            .state('educationalQualification.delete', {
                parent: 'educationalQualification',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/educationalQualification/educationalQualification-delete-dialog.html',
                        controller: 'EducationalQualificationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['EducationalQualification', function(EducationalQualification) {
                                return EducationalQualification.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('educationalQualification', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

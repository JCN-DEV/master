'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jpAcademicQualification', {
                parent: 'entity',
                url: '/jpAcademicQualifications',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpAcademicQualification.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpAcademicQualification/jpAcademicQualifications.html',
                        controller: 'JpAcademicQualificationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpAcademicQualification');
                        $translatePartialLoader.addPart('resultType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jpAcademicQualification.detail', {
                parent: 'entity',
                url: '/jpAcademicQualification/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpAcademicQualification.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpAcademicQualification/jpAcademicQualification-detail.html',
                        controller: 'JpAcademicQualificationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpAcademicQualification');
                        $translatePartialLoader.addPart('resultType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpAcademicQualification', function($stateParams, JpAcademicQualification) {
                        return JpAcademicQualification.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jpAcademicQualification.new', {
                parent: 'jpAcademicQualification',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpAcademicQualification.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpAcademicQualification/jpAcademicQualification-form.html',
                        controller: 'JpAcademicQualificationFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpAcademicQualification');
                        $translatePartialLoader.addPart('resultType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpAcademicQualification', function($stateParams, JpAcademicQualification) {

                    }]
                }

            })
            .state('jpAcademicQualification.edit', {
                parent: 'jpAcademicQualification',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpAcademicQualification.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpAcademicQualification/jpAcademicQualification-form.html',
                        controller: 'JpAcademicQualificationFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpAcademicQualification');
                        $translatePartialLoader.addPart('resultType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpAcademicQualification', function($stateParams, JpAcademicQualification) {
                        console.log($stateParams.id);
                        return JpAcademicQualification.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jpAcademicQualification.delete', {
                parent: 'jpAcademicQualification',
                url: '/{id}/delete',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jpAcademicQualification/jpAcademicQualification-delete-dialog.html',
                        controller: 'JpAcademicQualificationrDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['JpAcademicQualification', function (JpAcademicQualification) {
                                return JpAcademicQualification.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('resume', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });

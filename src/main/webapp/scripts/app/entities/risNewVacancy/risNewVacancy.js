'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('risNewVacancy', {
                parent: 'entity',
                url: '/risNewVacancys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.risNewVacancy.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/risNewVacancy/risNewVacancys.html',
                        controller: 'RisNewVacancyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewVacancy');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('risNewVacancy.detail', {
                parent: 'entity',
                url: '/risNewVacancy/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.risNewVacancy.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/risNewVacancy/risNewVacancy-detail.html',
                        controller: 'RisNewVacancyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risNewVacancy');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RisNewVacancy', function($stateParams, RisNewVacancy) {
                        return RisNewVacancy.get({id : $stateParams.id});
                    }]
                }
            })
            .state('risNewVacancy.new', {
                parent: 'risNewVacancy',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/risNewVacancy/risNewVacancy-dialog.html',
                        controller: 'RisNewVacancyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    vacancyNo: null,
                                    educationalQualification: null,
                                    otherQualification: null,
                                    remarks: null,
                                    publishDate: null,
                                    applicationDate: null,
                                    attachment: null,
                                    attachmentContentType: null,
                                    createdDate: null,
                                    updatedDate: null,
                                    createdBy: null,
                                    updatedBy: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('risNewVacancy', null, { reload: true });
                    }, function() {
                        $state.go('risNewVacancy');
                    })
                }]
            })
            .state('risNewVacancy.edit', {
                parent: 'risNewVacancy',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/risNewVacancy/risNewVacancy-dialog.html',
                        controller: 'RisNewVacancyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RisNewVacancy', function(RisNewVacancy) {
                                return RisNewVacancy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('risNewVacancy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

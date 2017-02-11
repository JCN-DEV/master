'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instVacancyTemp', {
                parent: 'entity',
                url: '/instVacancyTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instVacancyTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instVacancyTemp/instVacancyTemps.html',
                        controller: 'InstVacancyTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instVacancyTemp');
                        $translatePartialLoader.addPart('empTypes');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instVacancyTemp.detail', {
                parent: 'entity',
                url: '/instVacancyTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instVacancyTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instVacancyTemp/instVacancyTemp-detail.html',
                        controller: 'InstVacancyTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instVacancyTemp');
                        $translatePartialLoader.addPart('empTypes');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstVacancyTemp', function($stateParams, InstVacancyTemp) {
                        return InstVacancyTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instVacancyTemp.new', {
                parent: 'instVacancyTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instVacancyTemp/instVacancyTemp-dialog.html',
                        controller: 'InstVacancyTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    dateCreated: null,
                                    dateModified: null,
                                    status: null,
                                    empType: null,
                                    totalVacancy: null,
                                    filledUpVacancy: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instVacancyTemp', null, { reload: true });
                    }, function() {
                        $state.go('instVacancyTemp');
                    })
                }]
            })
            .state('instVacancyTemp.edit', {
                parent: 'instVacancyTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instVacancyTemp/instVacancyTemp-dialog.html',
                        controller: 'InstVacancyTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstVacancyTemp', function(InstVacancyTemp) {
                                return InstVacancyTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instVacancyTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

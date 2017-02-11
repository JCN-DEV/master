'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tempEmployer', {
                parent: 'entity',
                url: '/tempEmployers',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_EMPLOYER'],
                    pageTitle: 'stepApp.tempEmployer.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tempEmployer/tempEmployers.html',
                        controller: 'TempEmployerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tempEmployer');
                        $translatePartialLoader.addPart('tempEmployerStatus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('tempEmployer.detail', {
                parent: 'entity',
                url: '/tempEmployer/{id}',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_EMPLOYER','ROLE_JPADMIN'],
                    pageTitle: 'stepApp.tempEmployer.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tempEmployer/tempEmployer-detail.html',
                        controller: 'TempEmployerDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tempEmployer');
                        $translatePartialLoader.addPart('tempEmployerStatus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TempEmployer', function($stateParams, TempEmployer) {
                        return TempEmployer.get({id : $stateParams.id});
                    }]
                }
            })
            .state('tempEmployer.new', {
                parent: 'tempEmployer',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tempEmployer/tempEmployer-dialog.html',
                        controller: 'TempEmployerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    alternativeCompanyName: null,
                                    contactPersonName: null,
                                    personDesignation: null,
                                    contactNumber: null,
                                    companyInformation: null,
                                    address: null,
                                    city: null,
                                    zipCode: null,
                                    companyWebsite: null,
                                    industryType: null,
                                    businessDescription: null,
                                    companyLogo: null,
                                    companyLogoContentType: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('tempEmployer', null, { reload: true });
                    }, function() {
                        $state.go('tempEmployer');
                    })
                }]
            })
            .state('tempEmployer.edit', {
                parent: 'tempEmployer',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_EMPLOYER','ROLE_JPADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tempEmployer/tempEmployer-dialog.html',
                        controller: 'TempEmployerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TempEmployer', function(TempEmployer) {
                                return TempEmployer.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tempEmployer', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

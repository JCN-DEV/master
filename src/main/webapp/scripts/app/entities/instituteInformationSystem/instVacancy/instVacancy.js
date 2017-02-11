'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instVacancy', {
                parent: 'instituteInfo',
                url: '/instVacancys',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instVacancy.home.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instVacancy/instVacancys.html',
                        controller: 'InstVacancyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instVacancy');
                        $translatePartialLoader.addPart('empTypes');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            }).state('instVacancyAdmin', {
                parent: 'instituteInfo',
                url: '/admin/instVacancys/:instituteId',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instVacancy.home.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instVacancy/instVacancys.html',
                        controller: 'InstVacancyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instVacancy');
                        $translatePartialLoader.addPart('empTypes');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instVacancy.detail', {
                parent: 'instituteInfo',
                url: '/instVacancy/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instVacancy.detail.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instVacancy/instVacancy-detail.html',
                        controller: 'InstVacancyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instVacancy');
                        $translatePartialLoader.addPart('empTypes');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstVacancy', function($stateParams, InstVacancy) {
                        return InstVacancy.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instVacancy.new', {
                parent: 'instituteInfo',
                url: '/vacancy-management/new',
                data: {
                    authorities: [],
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instVacancy/instVacancy-dialog.html',
                        controller: 'InstVacancyDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instVacancy');
                        $translatePartialLoader.addPart('empTypes');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstVacancy', function($stateParams, InstVacancy) {
                        return InstVacancy.get({id : $stateParams.id});
                    }]
                }
            }).state('instVacancy.add', {
                parent: 'instVacancyAdmin',
                url: '/vacancy-management/add',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instVacancy/instVacancy-dialog.html',
                        controller: 'InstVacancyDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instVacancy');
                        $translatePartialLoader.addPart('empTypes');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstVacancy', function($stateParams, InstVacancy) {
                        return InstVacancy.get({id : $stateParams.id});
                    }]
                }
            })
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instVacancy/instVacancy-dialog.html',
                        controller: 'InstVacancyDialogController',
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
                        $state.go('instVacancy', null, { reload: true });
                    }, function() {
                        $state.go('instVacancy');
                    })
                }]
            })*/
            .state('instVacancy.edit', {
                parent: 'instituteInfo',
                url: '/vacancy-management/{id}/edit',
                data: {
                    authorities: [],
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instVacancy/instVacancy-dialog.html',
                        controller: 'InstVacancyDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instVacancy');
                        $translatePartialLoader.addPart('empTypes');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstVacancy', function($stateParams, InstVacancy) {
                        return InstVacancy.get({id : $stateParams.id});
                    }]
                }
            })
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instVacancy/instVacancy-dialog.html',
                        controller: 'InstVacancyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstVacancy', function(InstVacancy) {
                                return InstVacancy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instVacancy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/
            .state('instVacancy.delete', {
                parent: 'instituteInfo',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instVacancy/instVacancy-delete-dialog.html',
                        controller: 'InstVacancyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstVacancy', function(InstVacancy) {
                                return InstVacancy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instVacancy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

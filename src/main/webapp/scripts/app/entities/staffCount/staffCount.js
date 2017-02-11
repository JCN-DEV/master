'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('staffCount', {
                parent: 'entity',
                url: '/staffCounts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.staffCount.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/staffCount/staffCounts.html',
                        controller: 'StaffCountController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('staffCount');
                        $translatePartialLoader.addPart('employeeType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('staffCount.detail', {
                parent: 'entity',
                url: '/staffCount/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.staffCount.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/staffCount/staffCount-detail.html',
                        controller: 'StaffCountDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('staffCount');
                        $translatePartialLoader.addPart('employeeType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'StaffCount', function($stateParams, StaffCount) {
                        return StaffCount.get({id : $stateParams.id});
                    }]
                }
            })
            .state('staffCount.new', {
                parent: 'staffCount',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/staffCount/staffCount-dialog.html',
                        controller: 'StaffCountDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    type: null,
                                    numberOfPrincipal: null,
                                    numberOfMaleTeacher: null,
                                    numberOfFemaleTeacher: null,
                                    numberOfDemonstrator: null,
                                    numberOfAssistantLibrarian: null,
                                    numberOfLabAssistant: null,
                                    numberOfScienceLabAssistant: null,
                                    thirdClass: null,
                                    fourthClass: null,
                                    numberOfFemaleAvailableByQuota: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('staffCount', null, { reload: true });
                    }, function() {
                        $state.go('staffCount');
                    })
                }]
            })
            .state('staffCount.edit', {
                parent: 'staffCount',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/staffCount/staffCount-dialog.html',
                        controller: 'StaffCountDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['StaffCount', function(StaffCount) {
                                return StaffCount.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('staffCount', null, { reload: true });
                    }, function() {
                        $state.go('staffCount');
                    })
                }]
            })
            .state('staffCount.delete', {
                parent: 'staffCount',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/staffCount/staffCount-delete-dialog.html',
                        controller: 'StaffCountDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['StaffCount', function(StaffCount) {
                                return StaffCount.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('staffCount', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

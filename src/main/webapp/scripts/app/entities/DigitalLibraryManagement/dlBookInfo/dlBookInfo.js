'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('libraryInfo.libraryBookInfos', {
                parent: 'libraryInfo',
                url: '/dlBookInfos',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlBookInfo.home.title'
                },
                views: {
                     'libraryView@libraryInfo':{
                       templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookInfo/dlBookInfos.html',
                       controller: 'DlBookInfoController'
                                       }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookInfo');
                        $translatePartialLoader.addPart('dlBookRequisition');

                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('libraryInfo.libraryBookInfos.detail', {
                parent: 'libraryInfo.libraryBookInfos',
                url: '/dlBookInfo/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlBookInfo.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookInfo/dlBookInfo-detail.html',
                        controller: 'DlBookInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlBookInfo', function($stateParams, DlBookInfo) {
                        return DlBookInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('libraryInfo.libraryBookInfos.new', {
                parent: 'libraryInfo.libraryBookInfos',
                url: '/new',
                data: {
                    authorities: [],
                },
                views: {
                     'libraryView@libraryInfo':{
                       templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookInfo/dlBookInfo-dialog.html',
                       controller: 'DlBookInfoDialogController',
                                       }
                },   resolve: {
                      entity: function () {
                          return {
                              title: null,
                              edition: null,
                              isbnNo: null,
                              authorName: null,
                              copyright: null,
                              publisherName: null,
                              libraryName: null,
                              callNo: null,
                              totalCopies: null,
                              purchaseDate: null,
                              createdDate: null,
                              updatedDate: null,
                              createdBy: null,
                              updatedBy: null,
                              status: null,
                              id: null
                          };
                      }
              }
            })

            .state('libraryInfo.libraryBookInfos.edit', {
                parent: 'libraryInfo.libraryBookInfos',
                url: '/edit/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.dlBookInfo.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                       templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookInfo/dlBookInfo-dialog.html',
                       controller: 'DlBookInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlBookInfo', function($stateParams, DlBookInfo) {
                        return DlBookInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('libraryInfo.libraryBookInfos.delete', {
                parent: 'libraryInfo.libraryBookInfos',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlBookInfo/dlBookInfo-delete-dialog.html',
                        controller: 'DlBookInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DlBookInfo', function(DlBookInfo) {
                                return DlBookInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlBookInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

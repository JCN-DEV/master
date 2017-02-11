'use strict';

angular.module('stepApp')
    .factory('DlBookInfo', function ($resource, DateUtils) {
        return $resource('api/dlBookInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.purchaseDate = DateUtils.convertLocaleDateFromServer(data.purchaseDate);
                    data.createdDate = DateUtils.convertLocaleDateFromServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateFromServer(data.updatedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.purchaseDate = DateUtils.convertLocaleDateToServer(data.purchaseDate);
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.purchaseDate = DateUtils.convertLocaleDateToServer(data.purchaseDate);
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('isbnBookInfo', function ($resource) {
          return $resource('api/dlBookInfos/findBookInfoByBookId/:bookId', {}, {
              'get': { method: 'GET', isArray: false}
          });
    })

    .factory('findByallType', function ($resource) {
          return $resource('api/dlBookInfos/findByallType/:dlContCatSet/:dlContTypeSet', {}, {
              'get': { method: 'GET', isArray: false}
          });
    })
        .factory('validationforisbn', function ($resource) {
            return $resource('api/dlBookInfo/dlBookInfoisbnvalidation/:isbnNo', {}, {
                'query': {method: 'GET', isArray: true}
            });
        })
        .factory('FindDlBookInfoByInstId', function ($resource) {
                      return $resource('api/dlBookInfos/FindDlBookInfosByInstId', {}, {
                          'query': { method: 'GET', isArray: true}
                      })
                    })

        .factory('getAllAuthorByBookTitle', function ($resource) {
                  return $resource('api/dlBookInfos/getAuthorListByTitle/:title', {}, {
                      'get': { method: 'GET', isArray: false}
                  });
            })

            .factory('validationForBookId', function ($resource) {
                        return $resource('api/dlBookInfo/dlBookInfoBookIdValidation/:bookId', {}, {
                            'query': {method: 'GET', isArray: true}
                        });
                    })

                    .factory('getAllBookInfoByScatAndInstitute', function ($resource) {
                                return $resource('api/dlBookInfos/getAllBookInfoByScatAndInstitute/:id', {}, {
                                    'get': { method: 'GET', isArray: true}
                                });
                    });

//        .factory('getAllEditionByAuthorName', function ($resource) {
//              return $resource('api/dlBookInfos/getEditionListByAuthorName/:authorName', {}, {
//                  'get': { method: 'GET', isArray: false}
//              });
//        })


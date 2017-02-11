'use strict';

angular.module('stepApp')
    .factory('DlContUpld', function ($resource, DateUtils) {
        return $resource('api/dlContUplds/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createdDate = DateUtils.convertLocaleDateFromServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateFromServer(data.updatedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('ContUpldByCode', function ($resource) {
      return $resource('api/dlContUplds/contUpldCodeUnique/:code', {}, {
          'query': {method: 'GET', isArray: true}
      });
    })
    .factory('findAllByAllType', function ($resource) {
      return $resource('api/dlContUplds/findAllByAllType/:dlContCatSet/:dlContSCatSet/:dlContTypeSet', {}, {
          'query': {method: 'GET', isArray: true}
      });
    })
     .factory('FindAllByAdminUpload', function ($resource) {
      return $resource('api/dlContUplds/byAdmin', {}, {
          'query': {method: 'GET', isArray: true}
      });
    })
     .factory('FindAllByUserUpload', function ($resource) {
      return $resource('api/dlContUplds/byUser', {}, {
          'query': {method: 'GET', isArray: true}
      });
    })

    .factory('FindAllByUserUploadByCurrentInstitiute', function ($resource) {
      return $resource('api/dlContUplds/FindDlContUpldsByInstId', {}, {
          'query': {method: 'GET', isArray: true}
      });
    })

.factory('findOneByIsbn', function ($resource) {
    return $resource('api/dlContUplds/dlContupldsetisbn/', {}, {
        'query': {method: 'GET', isArray: true}
    });
});

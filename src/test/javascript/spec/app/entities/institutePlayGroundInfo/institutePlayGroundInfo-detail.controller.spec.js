'use strict';

describe('InstitutePlayGroundInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstitutePlayGroundInfo, MockInstituteInfraInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstitutePlayGroundInfo = jasmine.createSpy('MockInstitutePlayGroundInfo');
        MockInstituteInfraInfo = jasmine.createSpy('MockInstituteInfraInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstitutePlayGroundInfo': MockInstitutePlayGroundInfo,
            'InstituteInfraInfo': MockInstituteInfraInfo
        };
        createController = function() {
            $injector.get('$controller')("InstitutePlayGroundInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:institutePlayGroundInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});

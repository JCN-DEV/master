'use strict';

describe('InstInfraInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstInfraInfo, MockInstitute, MockInstBuilding, MockInstLand;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstInfraInfo = jasmine.createSpy('MockInstInfraInfo');
        MockInstitute = jasmine.createSpy('MockInstitute');
        MockInstBuilding = jasmine.createSpy('MockInstBuilding');
        MockInstLand = jasmine.createSpy('MockInstLand');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstInfraInfo': MockInstInfraInfo,
            'Institute': MockInstitute,
            'InstBuilding': MockInstBuilding,
            'InstLand': MockInstLand
        };
        createController = function() {
            $injector.get('$controller')("InstInfraInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instInfraInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});

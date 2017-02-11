'use strict';

describe('InstInfraInfoTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstInfraInfoTemp, MockInstitute, MockInstBuilding, MockInstLand;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstInfraInfoTemp = jasmine.createSpy('MockInstInfraInfoTemp');
        MockInstitute = jasmine.createSpy('MockInstitute');
        MockInstBuilding = jasmine.createSpy('MockInstBuilding');
        MockInstLand = jasmine.createSpy('MockInstLand');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstInfraInfoTemp': MockInstInfraInfoTemp,
            'Institute': MockInstitute,
            'InstBuilding': MockInstBuilding,
            'InstLand': MockInstLand
        };
        createController = function() {
            $injector.get('$controller')("InstInfraInfoTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instInfraInfoTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});

'use strict';

describe('InstAcaInfoTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstAcaInfoTemp, MockInstitute;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstAcaInfoTemp = jasmine.createSpy('MockInstAcaInfoTemp');
        MockInstitute = jasmine.createSpy('MockInstitute');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstAcaInfoTemp': MockInstAcaInfoTemp,
            'Institute': MockInstitute
        };
        createController = function() {
            $injector.get('$controller')("InstAcaInfoTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instAcaInfoTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
